package corecord.dev.domain.user.entity;

import corecord.dev.common.base.BaseEntity;
import corecord.dev.domain.Ability.entity.Ability;
import corecord.dev.domain.chat.entity.ChatRoom;
import corecord.dev.domain.folder.entity.Folder;
import corecord.dev.domain.record.entity.Record;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String providerId;

    @Setter
    @Column(nullable = false)
    private String nickName;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private Long tmpChat;

    @Column
    private Long tmpMemo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Record> records;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ability> abilities;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Folder> folders;

    public void updateTmpMemo(Long tmpMemo) {
        this.tmpMemo = tmpMemo;
    }

    public void updateTmpChat(Long tmpChat) {
        this.tmpChat = tmpChat;
    }

    public void deleteTmpMemo(){
        this.tmpMemo = null;
    }

    public void deleteTmpChat(){
        this.tmpChat = null;
    }
}
