package corecord.dev.domain.record.constant;

import corecord.dev.common.base.BaseSuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecordSuccessStatus implements BaseSuccessStatus {

    MEMO_RECORD_CREATE_SUCCESS(HttpStatus.CREATED, "S404", "메모 경험 기록이 성공적으로 완료되었습니다."),
    MEMO_RECORD_DETAIL_GET_SUCCESS(HttpStatus.OK, "S401", "메모 경험 기록 세부 조회가 성공적으로 완료되었습니다."),
    MEMO_RECORD_TMP_CREATE_SUCCESS(HttpStatus.OK, "S403", "메모 경험 기록 임시 저장이 성공적으로 완료되었습니다."),
    MEMO_RECORD_TMP_GET_SUCCESS(HttpStatus.OK, "S402", "메모 경험 기록 임시 저장 내역 조회가 성공적으로 완료되었습니다."),
    RECORD_LIST_GET_SUCCESS(HttpStatus.OK, "S602", "폴더별 경험 기록 리스트 조회가 성공적으로 완료되었습니다.")
            ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
