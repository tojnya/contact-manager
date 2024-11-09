package ua.com.javarush.gnew.m2.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.com.javarush.gnew.m2.dto.ContactDto;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileContactDtoRepository implements ContactDtoRepository {
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    ArrayList<ContactDto> User = new ArrayList<>();
    @Override
    public List<ContactDto> findAll() throws IOException {
        File file = new File(User + "book.st");
        boolean fileCreated = file.createNewFile();
        if (fileCreated) {
            System.out.println("Файл был создан: " + file.getAbsolutePath());
        } else {
            System.out.println("Не удалось создать файл.");
        }
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ContactDto.class));
    }
    @Override
    public Optional<ContactDto> findById(long id) throws IOException {
        return findAll().stream().filter(contact -> contact.getId() == id).findFirst();
    }
    @Override
    public void deleteById(long id) throws IOException {
        List<ContactDto> contacts = findAll();
        contacts.removeIf(contact -> contact.getId() == id);
        saveAll(contacts);
    }
    @Override
    public void saveAll(List<ContactDto> contacts) throws IOException {
        objectMapper.writeValue(new File(User + "book.st"),contacts);
    }
    @Override
    public void save(ContactDto contactDto) throws IOException {
        List<ContactDto> contacts = findAll();
        if (contactDto.getId() == 0) {
            contactDto.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        }

        contacts.add(contactDto);
        saveAll(contacts);
    }

    
    @Override
    public List<ContactDto> findByKeyword(String keyword) throws IOException {
        List<ContactDto> contacts = findAll();
        return contacts.stream()
                .filter(contact -> contact.getFullName().contains(keyword) ||
                        contact.getPhones().stream().anyMatch(phones -> phones.contains(keyword)) ||
                        contact.getEmails().stream().anyMatch(emails -> emails.contains(keyword)))
                .collect(Collectors.toList());
    }
}
