package kr.KWGraduate.BookPharmacy.domain.onelineprescription.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response.OneLineResponseDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.service.OneLinePrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oneline-prescriptions")
@RequiredArgsConstructor
@Tag(name = "한줄처방 api")
public class OneLinePrescriptionController {

    private final OneLinePrescriptionService oneLinePrescriptionService;

    @Operation(summary = "모든 한줄처방 리스트를 페이징하여 요청", description = "page와 size를 입력받아 이를 반환")
    @GetMapping("/all")
    public ResponseEntity<Page<OneLineResponseDto>> getAllOneLinePrescriptions(@RequestParam(name = "page") int page,
                                                                               @RequestParam(name = "size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getAllOneLinePrescriptions(pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "나의 한줄처방 리스트를 페이징하여 요청", description = "page와 size를 입력받아 이를 반환")
    @GetMapping("/my")
    public ResponseEntity<Page<OneLineResponseDto>> getMyOneLinePrescriptions(@RequestParam(name = "page") int page,
                                                                              @RequestParam(name = "size") int size,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getMyOneLinePrescriptions((AuthenticationAdapter) userDetails, pageRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/keyword")
    @Operation(summary = "한줄처방 키워드 별 조회", description = "무한 스크롤을 위한 size와 page입력 필수, 키워드 입력")
    public ResponseEntity<Page<OneLineResponseDto>> getOneLinePrescriptionsByKeyword(@RequestParam(name = "keyword") Keyword keyword,
                                                                                     @RequestParam("page") int page,
                                                                                     @RequestParam("size") int size)
    {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getOneLinePrescriptionsByKeyword(keyword, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "검색어를 포함하는 한줄처방 리스트를 페이징 조회", description = "무한 스크롤을 위한 size와 page입력 필수, 검색어 입력")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<OneLineResponseDto>> getOneLinePrescriptionsBySearch(@RequestParam(name = "name") String searchWord,
                                                                                @RequestParam("page") int page,
                                                                                @RequestParam("size") int size)
    {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getOneLinePrescriptionsBySearch(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "한줄처방을 생성하도록 요청")
    @PostMapping("/new")
    public ResponseEntity<Void> createOneLinePrescription(@RequestBody OneLineCreateDto oneLineCreateDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.createOneLinePrescription(oneLineCreateDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "한줄처방을 단건으로 조회할 때 요청")
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<OneLineResponseDto> getOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId) {
        OneLineResponseDto result = oneLinePrescriptionService.getOneLinePrescription(prescriptionId);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "한줄처방을 수정할때 요청")
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<String> updateOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                          @RequestBody OneLineUpdateDto oneLineUpdateDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.updateOneLinePrescription(prescriptionId, oneLineUpdateDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "한줄처방을 삭제할 때 요청")
    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<String> deleteOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.deleteOneLinePrescription(prescriptionId, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok("success");
    }

}
