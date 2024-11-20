package corecord.dev.domain.folder.application;

import corecord.dev.common.exception.GeneralException;
import corecord.dev.common.status.ErrorStatus;
import corecord.dev.domain.folder.domain.converter.FolderConverter;
import corecord.dev.domain.folder.domain.dto.request.FolderRequest;
import corecord.dev.domain.folder.domain.dto.response.FolderResponse;
import corecord.dev.domain.folder.domain.entity.Folder;
import corecord.dev.domain.folder.status.FolderErrorStatus;
import corecord.dev.domain.folder.exception.FolderException;
import corecord.dev.domain.folder.domain.repository.FolderRepository;
import corecord.dev.domain.user.domain.entity.User;
import corecord.dev.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    /*
     * 폴더명(title)을 request로 받아, 새로운 폴더를 생성 후 생성 순 풀더 리스트 반환
     * @param userId, folderDto
     * @return
     */
    @Transactional
    public FolderResponse.FolderDtoList createFolder(Long userId, FolderRequest.FolderDto folderDto) {
        User user = findUserById(userId);
        String title = folderDto.getTitle();

        // 폴더명 유효성 검증
        validDuplicatedFolderTitleAndLength(title);

        // folder 객체 생성 및 User 연관관계 설정
        Folder folder = FolderConverter.toFolderEntity(title, user);
        folderRepository.save(folder);

        List<FolderResponse.FolderDto> folderList = folderRepository.findFolderDtoList(user);
        return FolderConverter.toFolderDtoList(folderList);
    }

    /*
     * folderId를 받아 folder를 삭제한 후 생성 순 폴더 리스트 반환
     * @param userId, folderId
     * @return
     */
    @Transactional
    public FolderResponse.FolderDtoList deleteFolder(Long userId, Long folderId) {
        User user = findUserById(userId);
        Folder folder = findFolderById(folderId);

        // User-Folder 권한 유효성 검증
        validIsUserAuthorizedForFolder(user, folder);

        folderRepository.delete(folder);

        List<FolderResponse.FolderDto> folderList = folderRepository.findFolderDtoList(user);
        return FolderConverter.toFolderDtoList(folderList);
    }

    /*
     * folderId를 받아, 해당 folder의 title을 수정
     * @param userId, folderDto
     * @return
     */
    @Transactional
    public FolderResponse.FolderDtoList updateFolder(Long userId, FolderRequest.FolderUpdateDto folderDto) {
        User user = findUserById(userId);
        Folder folder = findFolderById(folderDto.getFolderId());
        String title = folderDto.getTitle();

        // 폴더명 유효성 검증
        validDuplicatedFolderTitleAndLength(title);

        // User-Folder 권한 유효성 검증
        validIsUserAuthorizedForFolder(user, folder);

        folder.updateTitle(title);

        List<FolderResponse.FolderDto> folderList = folderRepository.findFolderDtoList(user);
        return FolderConverter.toFolderDtoList(folderList);
    }

    /*
     * 생성일 오름차순으로 폴더 리스트를 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public FolderResponse.FolderDtoList getFolderList(Long userId) {
        User user = findUserById(userId);

        List<FolderResponse.FolderDto> folderList = folderRepository.findFolderDtoList(user);
        return FolderConverter.toFolderDtoList(folderList);
    }

    private void validDuplicatedFolderTitleAndLength(String title) {
        // 폴더명 글자 수 검사
        if (title.length() > 15) {
            throw new FolderException(FolderErrorStatus.OVERFLOW_FOLDER_TITLE);
        }

        // 폴더명 중복 검사
        if (folderRepository.existsByTitle(title)) {
            throw new FolderException(FolderErrorStatus.DUPLICATED_FOLDER_TITLE);
        }
    }

    // user-folder 권한 검사
    private void validIsUserAuthorizedForFolder(User user, Folder folder) {
        if (!folder.getUser().equals(user))
            throw new FolderException(FolderErrorStatus.USER_FOLDER_UNAUTHORIZED);
    }

    private Folder findFolderById(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(FolderErrorStatus.FOLDER_NOT_FOUND));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));
    }
}