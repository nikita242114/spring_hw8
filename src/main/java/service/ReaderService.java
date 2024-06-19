package service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import model.Issue;
import model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.IssueRepository;
import repository.ReaderRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, IssueRepository issueRepository) {
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }

    // получить читателя по id
    public Reader getReaderById(Long id) {
        return readerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //получить список всех читателей
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    // создание читателя
    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    //обновление читателей
    @Transactional
    public Reader updateReaders(Long id, Reader reader) {
        Reader updateReader = readerRepository.findById(id).orElseThrow(()-> new RuntimeException("Reader not found"));
        updateReader.setName(reader.getName());
        return readerRepository.save(updateReader);
    }

    // удаление читателя
    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    //получить список выдач по id читателя
    public List<Issue> getAllIssueByReaderId(Long id) {
        return issueRepository.getAllIssueByReaderId(id);
    }

}