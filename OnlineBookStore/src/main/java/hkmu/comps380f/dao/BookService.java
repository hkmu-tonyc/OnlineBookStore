package hkmu.comps380f.dao;

import hkmu.comps380f.exception.BookNotFound;
import hkmu.comps380f.exception.CoverPhotoNotFound;
import hkmu.comps380f.model.Book;
import hkmu.comps380f.model.CoverPhoto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    @Resource
    private BookRepository bRepo;
    @Resource
    private CoverPhotoRepository cRepo;
    @Transactional
    public List<Book> getBooks() {
        return bRepo.findAll();
    }
    @Transactional
    public Book getBook(long id)
            throws BookNotFound {
        Book book = bRepo.findById(id).orElse(null);
        if (book == null) {
            throw new BookNotFound(id);
        }
        return book;
    }
    @Transactional
    public CoverPhoto getCoverPhoto(long bookId, UUID coverPhotoId)
            throws BookNotFound, CoverPhotoNotFound {
        Book book = bRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        CoverPhoto coverPhoto = cRepo.findById(coverPhotoId).orElse(null);
        if (coverPhoto == null) {
            throw new CoverPhotoNotFound(coverPhotoId);
        }
        return coverPhoto;
    }
    @Transactional(rollbackFor = BookNotFound.class)
    public void delete(long id) throws BookNotFound {
        Book deletedBook = bRepo.findById(id).orElse(null);
        if (deletedBook == null) {
            throw new BookNotFound(id);
        }
        bRepo.delete(deletedBook);
    }
    @Transactional(rollbackFor = CoverPhotoNotFound.class)
    public void deleteCoverPhoto(long bookId, UUID coverPhotoId)
            throws BookNotFound, CoverPhotoNotFound {
        Book book = bRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        for (CoverPhoto coverPhoto : book.getCoverPhotos()) {
            if (coverPhoto.getId().equals(coverPhotoId)) {
                book.deleteCoverPhoto(coverPhoto);
                bRepo.save(book);
                return;
            }
        }
        throw new CoverPhotoNotFound(coverPhotoId);
    }

    @Transactional
    public long createBook(String bookName, String author,
                             int price, String description, List<MultipartFile> coverPhotos)
            throws IOException {
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPrice(price);
        book.setDescription(description);
        for (MultipartFile filePart : coverPhotos) {
            CoverPhoto coverPhoto = new CoverPhoto();
            coverPhoto.setName(filePart.getOriginalFilename());
            coverPhoto.setMimeContentType(filePart.getContentType());
            coverPhoto.setContents(filePart.getBytes());
            coverPhoto.setBook(book);
            if (coverPhoto.getName() != null && coverPhoto.getName().length() > 0
                    && coverPhoto.getContents() != null
                    && coverPhoto.getContents().length > 0) {
                book.getCoverPhotos().add(coverPhoto);
            }
        }
        Book savedBook = bRepo.save(book);
        return savedBook.getId();
    }

    @Transactional(rollbackFor = BookNotFound.class)
    public void updateBook(long id, String bookName,
                           String author, int price, String description, List<MultipartFile> coverPhotos)
            throws IOException, BookNotFound {
        Book updatedBook = bRepo.findById(id).orElse(null);
        if (updatedBook == null) {
            throw new BookNotFound(id);
        }
        updatedBook.setBookName(bookName);
        updatedBook.setAuthor(author);
        updatedBook.setPrice(price);
        updatedBook.setDescription(description);
        for (MultipartFile filePart : coverPhotos) {
            CoverPhoto coverPhoto = new CoverPhoto();
            coverPhoto.setName(filePart.getOriginalFilename());
            coverPhoto.setMimeContentType(filePart.getContentType());
            coverPhoto.setContents(filePart.getBytes());
            coverPhoto.setBook(updatedBook);
            if (coverPhoto.getName() != null && coverPhoto.getName().length() > 0
                    && coverPhoto.getContents() != null
                    && coverPhoto.getContents().length > 0) {
                updatedBook.getCoverPhotos().add(coverPhoto);
            }
        }
        bRepo.save(updatedBook);
    }
}
