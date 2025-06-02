package com.example.tutorial12.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.tutorial12.model.BookDTO;
import com.example.tutorial12.services.BookService;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path="/api/books",
produces = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE})
public class BookApiController {

    private final BookService bookService;
    private final ObjectMapper objectMapper;

    public BookApiController(BookService bookService, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @Tag(name = "GET", description = "Get information about books")
    @GetMapping
    public Iterable<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    @Tag(name = "GET", description = "Get information about books")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable int id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "POST", description = "Add new book")
    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.saveBook(bookDTO);
        URI savedBookLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getBookID())
                .toUri();
        return ResponseEntity.created(savedBookLocation).body(savedBook);
    }

    @Tag(name = "POST", description = "Replace book entity")
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return bookService.replaceBook(id, bookDTO)
                .map(book -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "PATCH", description = "Update book information")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody JsonMergePatch patch) {
        try {
            BookDTO bookDTO = bookService.getBookById(id).orElseThrow();
            BookDTO patchedBookDTO = applyPatch(bookDTO, patch);
            bookService.updateBook(patchedBookDTO);
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }

    private BookDTO applyPatch(BookDTO bookDTO, JsonMergePatch patch) throws JsonProcessingException, JsonPatchException {
        JsonNode bookNode = objectMapper.valueToTree(bookDTO);
        JsonNode patchNode = patch.apply(bookNode);
        return objectMapper.treeToValue(patchNode, BookDTO.class);
    }

    @Tag(name = "DELETE", description = "Remove book")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
