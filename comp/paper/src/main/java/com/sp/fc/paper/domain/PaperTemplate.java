package com.sp.fc.paper.domain;

import com.sp.fc.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="sp_paper_template")
public class PaperTemplate { // " 선생님이 작성하는 곳 "

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paperTemplateId;

    private String name; // 시험지 이름

    private Long userId; // 작성자 아이디

    @Transient
    private User creator;

    private int total;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name="paperTemplateId"))
    private List<Problem> problemList;

    private long publishedCount;

    private long completeCount;

    @Column(updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

}
