package org.example.tutorial6.service;

import net.datafaker.Faker;
import org.example.tutorial6.exceptions.InvalidNumberOfEntriesException;
import org.example.tutorial6.model.Person;
import org.example.tutorial6.model.DataDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FakeDataService {

    public List<Person> generateData(DataDTO dto) throws InvalidNumberOfEntriesException {
        List<Person> people = new ArrayList<>();
        int entries = dto.getEntries();
        String language = dto.getLanguage();
        List<String> fields = dto.getFields();

        Faker faker = new Faker(Locale.of(language));

        if (entries < 1){
            throw new InvalidNumberOfEntriesException("Invalid number of entries provided");
        }

        for (int i = 0; i < entries; i++) {
            var person = new Person();

            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setDateOfBirth(faker.timeAndDate().birthday(18, 50, "dd-MM-yyyy"));

            if(fields != null && !fields.isEmpty()){
                for (String field : fields) {
                    switch (field) {
                        case "address"-> person.setAddress(faker.address().fullAddress());
                        case "university"-> person.setUniversity(faker.university().name());
                        case "country"-> person.setCountry(faker.country().name());
                        case "email"-> person.setEmail(faker.internet().emailAddress());
                        case "phoneNumber"-> person.setPhoneNumber(faker.phoneNumber().cellPhone());
                        case "gender"-> person.setGender(faker.demographic().sex());
                        case "job"-> person.setJob(faker.job().title());
                        case "company" -> person.setCompany(faker.company().name());
                    }
                }
            }

            people.add(person);
        }

        return people;
    }

    public List<String> generateHeaders(DataDTO dto) {
        List<String> headers = new ArrayList<>();
        String language = dto.getLanguage();

        try(var lines = Files.lines(ResourceUtils.getFile("classpath:translations.csv").toPath())) {
            lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> line.split(",", 2))
                    .skip(1)
                    .filter(parts -> parts[0].trim().equals(language))
                    .forEach(parts -> headers.add(parts[1].trim()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return headers;
    }
}