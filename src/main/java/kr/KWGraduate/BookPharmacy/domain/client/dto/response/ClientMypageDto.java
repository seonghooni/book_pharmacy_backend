package kr.KWGraduate.BookPharmacy.domain.client.dto.response;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientMypageDto {

    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private Client.Gender gender;
    private String occupation;
    private LocalDate birth;
    private Integer passwordLength;
    private String description;

    @Builder
    public ClientMypageDto(Client client) {
        this.loginId = client.getLoginId();
        this.name = client.getName();
        this.nickname = client.getNickname();
        this.email = client.getEmail();
        this.gender = client.getGender();
        this.occupation = client.getOccupation().getKoreanOccupation();
        this.birth = birth;
        this.passwordLength = passwordLength;
        this.description = description;
    }

}