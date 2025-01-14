package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.KeywordItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.CGLIB_CLASS_SEPARATOR;

@Data
public class BookDto {

    private Long bookId;
    private String title;
    private String author; // 저자명
    private String publishingHouse; // 출판사명
    private String publicYear; // 발행년도
    private String content; // 책내용
    private String mediaFlagNumber; // 미디어구분명
    private String middleCategoryName; // 중분류명
    private String imageUrl; // 이미지 URL
    private List<KeywordItemDto> keywordItemList; // 키워드 리스트

    public BookDto(Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishingHouse = book.getPublishingHouse();
        this.publicYear = book.getPublishYear();
        this.content = book.getContent();
        this.mediaFlagNumber = book.getMediaFlagNumber();
        this.middleCategoryName = book.getMiddleCategory().getName();
        this.imageUrl = book.getImageUrl();
    }

    @Builder
    public BookDto(Long bookId, String title, String author, String publishingHouse, String publicYear, String content, String mediaFlagNumber,
                   String middleCategoryName, String imageUrl) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.publicYear = publicYear;
        this.content = content;
        this.mediaFlagNumber = mediaFlagNumber;
        this.middleCategoryName = middleCategoryName;
        this.imageUrl = imageUrl;
    }

    /**
     * Book 리스트를 BookDto 리스트로 변환하는 함수
     * */
    public static List<BookDto> toDtoList(List<Book> books){
        List<BookDto> bookDtoList = books.stream()
                .map(book -> toDto(book))
                .collect(Collectors.toList());

        return bookDtoList;
    }

    /**
     * Book 리스트를 BookDto 리스트로 변환하는 함수 (키워드 DTO도 필요한 경우 사용)
     * */
    public static List<BookDto> toDtoListWithKeywordDto(List<Book> books){
        List<BookDto> bookDtoList = books.stream()
                .map(book -> toDtoWithKeywordDto(book))
                .collect(Collectors.toList());

        return bookDtoList;
    }

    /**
     * Book를 BookDto으로 변환하는 함수
     * */
    public static BookDto toDto(Book book){
        BookDto bookDto = new BookDto(book);

        return bookDto;
    }

    public static BookDto toDtoWithKeywordDto(Book book) {
        BookDto bookDto = new BookDto(book);
        List<KeywordItem> keywordItemList = book.getBookKeywords().stream().map(bookKeyword -> bookKeyword.getKeywordItem()).toList();
        bookDto.setKeywordItemDto(keywordItemList);

        return bookDto;
    }

    public static Page<BookDto> toDtoPageWithKeywordDto(Page<Book> bookPageList) {
        Page<BookDto> bookDtoPage = bookPageList.map(book -> BookDto.toDtoWithKeywordDto(book));

        return bookDtoPage;
    }


    private void setKeywordItemDto(List<KeywordItem> keywordItemList){
        this.keywordItemList = KeywordItemDto.toDtoList(keywordItemList);
    }

    /**
     * BookDto를 Book으로 변환하는 함수
     * */
    public static Book toEntity(BookDto bookDto){
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publishingHouse(bookDto.getPublishingHouse())
                .publishYear(bookDto.getPublicYear())
                .content(bookDto.getContent())
                .imageUrl(bookDto.getImageUrl())
                .build();

        return book;
    }
}
