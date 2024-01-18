package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.BookSearchDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto> getBookListByMiddleCategory(int pageNumber, int pageSize, String categoryName){

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findBookPagingByMiddleCategory(categoryName, pageRequest);

        List<Book> bookList = bookPageList.getContent();
        List<BookDto> bookDtoList = BookDto.toDtoList(bookList);

        return bookDtoList;
    }


    /**
     * 검색어와 paging size를 입력받으면, 해당하는 책 dto 리스트를 반환
     */
    public List<BookSearchDto> searchBySearchWord(int pageNumber, int pageSize, String searchKeyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchKeyword, pageRequest);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchDto> bookSearchDtoList = BookSearchDto.toSearchDtoList(bookList);

        return bookSearchDtoList;
    }

    public BookDto getBookDetails(String isbn){
        Optional<Book> optional = bookRepository.findOptionalByIsbn(isbn);

        BookDto bookDto = optional.map(book -> new BookDto(book))
                .orElseGet(() -> BookDto.builder().title("해당 책이 없습니다.").build());

        return bookDto;
    }

}