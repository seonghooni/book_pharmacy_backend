package kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OneLineResponseDto {
    private Long id;
    private String title;
    private String description;
    private String bookTitle;
    private String bookIsbn;
    private String bookAuthor;
    private String bookImageUrl;
    private String bookPublishYear;
    private String bookPublishingHouse;
    private String clientNickname;
    private LocalDate createdDate;

    @Builder
    public OneLineResponseDto(Long id, String title, String description, String bookTitle, String bookIsbn, String bookAuthor, String bookImageUrl,
                              String bookPublishYear, String bookPublishingHouse) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookImageUrl = bookImageUrl;
        this.bookPublishYear = bookPublishYear;
        this.bookPublishingHouse = bookPublishingHouse;
    }

    public OneLineResponseDto setAllAttr(Book book, Client client, OneLinePrescription oneLinePrescription) {
        this.setBookAttr(book);
        this.setClientAttr(client);
        this.setOneLinePrescriptionAttr(oneLinePrescription);

        return this;
    }

    private OneLineResponseDto setBookAttr(Book book) {
        this.bookTitle = book.getTitle();
        this.bookIsbn = book.getIsbn();
        this.bookAuthor = book.getAuthor();
        this.bookImageUrl = book.getImageUrl();
        this.bookPublishYear = book.getPublishYear();
        this.bookPublishingHouse = book.getPublishingHouse();

        return this;
    }

    private OneLineResponseDto setClientAttr(Client client) {
        this.clientNickname = client.getNickname();

        return this;
    }

    private OneLineResponseDto setOneLinePrescriptionAttr(OneLinePrescription oneLinePrescription) {
        this.id = oneLinePrescription.getId();
        this.title = oneLinePrescription.getTitle();
        this.description = oneLinePrescription.getDescription();
        this.createdDate = oneLinePrescription.getCreatedDate().toLocalDate();

        return this;
    }
}
