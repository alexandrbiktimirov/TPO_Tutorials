package org.example.tutorial11.service;

import org.example.tutorial11.exception.InvalidPasswordException;
import org.example.tutorial11.exception.LinkDoesNotExistException;
import org.example.tutorial11.model.Link;
import org.example.tutorial11.model.dto.CreateLinkDTO;
import org.example.tutorial11.model.dto.LinkDTOReturn;
import org.example.tutorial11.model.dto.UpdateLinkDTO;
import org.example.tutorial11.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String generateRandomId(){
        int length = 10;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

    public LinkDTOReturn createLink(CreateLinkDTO link) {
        var newLink = new Link();

        String id;

        do {
            id = generateRandomId();
        } while (linkRepository.findById(id).isPresent());

        newLink.setId(id);
        newLink.setName(link.name);
        newLink.setTargetUrl(link.targetUrl);
        newLink.setRedirectUrl("http://localhost:8080/red/" + id);

        if (link.getPassword().isPresent()) {
            newLink.setPassword(link.getPassword().get());
        }

        newLink.setVisits(0);

        linkRepository.save(newLink);

        return new LinkDTOReturn(newLink.getId(), newLink.getName(), newLink.getTargetUrl(), newLink.getRedirectUrl(), newLink.getVisits());
    }

    public LinkDTOReturn getLink(String id) throws LinkDoesNotExistException {
        var link = linkRepository.findById(id).orElseThrow(() -> new LinkDoesNotExistException("Link does not exist"));

        return new LinkDTOReturn(link.getId(), link.getName(), link.getTargetUrl(), link.getRedirectUrl(), link.getVisits());
    }

    public String getRedirectLink(String id) throws LinkDoesNotExistException {
        var link = linkRepository.findById(id).orElseThrow(() -> new LinkDoesNotExistException("Link does not exist"));
        link.setVisits(link.getVisits() + 1);
        linkRepository.save(link);

        return link.getTargetUrl();
    }

    public void updateLink(String id, UpdateLinkDTO updateLinkDTO) throws LinkDoesNotExistException, InvalidPasswordException {
        var link = linkRepository.findById(id).orElseThrow(() -> new LinkDoesNotExistException("Link does not exist"));

        if (link.getPassword() != null){
            if (updateLinkDTO.password == null || !updateLinkDTO.password.equals(link.getPassword())){
                throw new InvalidPasswordException("Wrong password");
            } else{
                if(updateLinkDTO.name != null){
                    link.setName(updateLinkDTO.name);
                }
                if(updateLinkDTO.targetUrl != null){
                    link.setTargetUrl(updateLinkDTO.targetUrl);
                }

                linkRepository.save(link);
            }
        }
    }

    public void deleteLink(String id, String input) throws LinkDoesNotExistException, InvalidPasswordException{
        var link = linkRepository.findById(id).orElseThrow(() -> new LinkDoesNotExistException("Link does not exist"));

        if (input == null){
            throw new InvalidPasswordException("Wrong password");
        }

        String password = input.replaceFirst("^pass:\\s*", "");

        if (link.getPassword() != null && link.getPassword().equals(password)){
            linkRepository.delete(link);
        } else{
            throw new InvalidPasswordException("Wrong password");
        }
    }
}
