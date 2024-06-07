package kopo.data.wordbook.app.notepad.service.implement;

import kopo.data.wordbook.app.notepad.controller.reponse.CreateNotepadResponse;
import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;
import kopo.data.wordbook.app.notepad.repository.NotepadRepository;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.notepad.service.INotepadService;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotepadService implements INotepadService {
    private final NotepadRepository notepadRepository;
    private final StudentRepository studentRepository;

    @Override
    public GetNotepadResponse getNotepad_ByNotepadIdAndStudentId(Long notepadSeq, String studentId) {
//        Optional<NotepadEntity> optional = notepadRepository.findById(notepadSeq);
        Optional<NotepadEntity> optional = notepadRepository.findByNotepadSeqAndRegStudent(
                notepadSeq,
                studentRepository.findById(studentId).get()
                );


        if (optional.isEmpty()) {
            log.warn("요청한 notepadSeq 가 비어있음!");
            return null;
        }

        log.trace("option.get() -> {}", optional.get());

        NotepadEntity notepadEntity = optional.get();
//         요청한 사용자와 id 가 같지 않다면...
        if (!notepadEntity.getRegStudent().getStudentId().equals(studentId)) {
            log.trace("notepadEntity 의 regStudentId -> {}", notepadEntity.getRegStudent().getStudentId());
            log.trace("파라미터로 넘어온 studentId -> {}", studentId);
            log.trace("파라미터로 넘어온 studentId -> {}", studentId.equals(notepadEntity.getRegStudent().getStudentId()));
            log.warn("요청한 사용자와 ID 가 같지 않음!");
            return null;
        }

        // 위 조건들을 통과하면 entity 를 return
        return GetNotepadResponse.builder()
                .notepadSeq(notepadEntity.getNotepadSeq())
                .content(notepadEntity.getContent())
                .regDate(notepadEntity.getRegDate())
                .chgDate(notepadEntity.getChgDate())
                .build();
    }

    @Override
    public List<GetNotepadResponse> getNotepadList_ByNotepadSeqAndStudentId(String studentId) {
        Optional<List<NotepadEntity>> optional = Optional.of(notepadRepository.findAll());


        if (optional.isEmpty()) {
            log.warn("전체 NotepadEntity 가 비어있습니다!!");
//            throw new RuntimeException("")
        }

        List<NotepadEntity> list = optional.get();

        // 로그 찍어보기
        list.stream().limit(2)
                .forEach(notepadEntity -> log.trace("notepad in list -> {}", notepadEntity));

        // 반환될 list
        List<GetNotepadResponse> resultList = new ArrayList<>();

        // studentId 가 맞는것만 남기도록 filtering 함!
        List<NotepadEntity> filteredList = list.stream()
                .filter(notepadEntity -> notepadEntity.getRegStudent().getStudentId().equals(studentId))
                .collect(Collectors.toList());

        // 필터링된 리스트 로그찍기
        filteredList.stream().limit(2)
                .forEach(notepadEntity -> log.trace("notepad in filteredList -> {}", notepadEntity));

        // 필터링된 리스트를 반환될 list 에 add 함
        filteredList.forEach(notepadEntity -> resultList.add(GetNotepadResponse.builder()
                .notepadSeq(notepadEntity.getNotepadSeq())
                .content(notepadEntity.getContent())
                .regDate(notepadEntity.getRegDate())
                .chgDate(notepadEntity.getChgDate())
                .build()));


        // 위 조건들을 통과하면 entity 를 return
        return resultList;
    }

    @Override
    public CreateNotepadResponse createNotepad(String content, String studentId) {

        StudentEntity register = this.findAndValidate(studentId);

        NotepadEntity savingNotepad = NotepadEntity.builder()
                .content(content)
                .regStudent(register)
                .chgStudent(register)
                .build();

        NotepadEntity savedNotepad = notepadRepository.save(savingNotepad);

        return CreateNotepadResponse.builder()
                .notepadSeq(savedNotepad.getNotepadSeq())
                .content(savedNotepad.getContent())
                .build();
    }

    @Override
    public CreateNotepadResponse updateNotepad(Long notepadSeq, String content, String studentId) {
        StudentEntity updater = this.findAndValidate(studentId);

        NotepadEntity existedNotepad = this.findAndValidate(notepadSeq, updater.getStudentId());

        log.trace("existedNotepad 바꾸기 전! -> {}", existedNotepad);

        // 굳이 전체를 바꿀필요는 없어보여서.. 안바꿈
        existedNotepad.setContent(content);

        log.trace("existedNotepad 바꾼 후! -> {}", existedNotepad);

        NotepadEntity savedNotepad = notepadRepository.save(existedNotepad);

        log.trace("저장된 notepadEntity 인 savedNotepad -> {}", savedNotepad);

        return CreateNotepadResponse.builder()
                .notepadSeq(savedNotepad.getNotepadSeq())
                .content(savedNotepad.getContent())
                .build();
    }

    @Override
    public CreateNotepadResponse deleteNotepad(Long notepadSeq, String studentId) {
        Optional<StudentEntity> student =  studentRepository.findById(studentId);

        if (student.isEmpty()) {
            // student 가 비어있을때 돌아갈 로직
            throw new RuntimeException("student 가 비어있음");

        }

        Optional<NotepadEntity> notepad = notepadRepository.findByNotepadSeqAndRegStudent(notepadSeq, student.get());

        if (notepad.isEmpty()) {
            // notepad 가 비어있을때 돌아갈 로직
            throw new RuntimeException("notepad 가 비어있음");
        }
        notepadRepository.deleteById(notepadSeq);

        Boolean isDeleteSuccess =
                notepadRepository.findByNotepadSeqAndRegStudent(notepadSeq, student.get()).isEmpty();

        log.trace("isDeleteSuccess -> {}", isDeleteSuccess);

        if (!isDeleteSuccess) {
            throw new RuntimeException("isDeleteSuccess 가 false 임!!");
        }

        return CreateNotepadResponse.builder()

                .build();
    }

    /**
     * notepadSeq 로 NotepadEntity 를 찾고, studentId를 통해 동일인이 만들었는지 확인
     *
     * @param notepadSeq NotepadEntity's PK
     * @param studentId  해당 notepad 의 주인이 맞는지 검증
     * @return if 문을 통과하면 리턴!
     * <p>
     * {{@link NotepadEntity}}
     */
    private NotepadEntity findAndValidate(Long notepadSeq, String studentId) {
        Optional<NotepadEntity> savingNotepad = notepadRepository.findById(notepadSeq);

        if (savingNotepad.isEmpty()) {
            throw new RuntimeException("해당 notepad 가 없음!");
        }

        if (!savingNotepad.get().getRegStudent().getStudentId().equals(studentId)) {
            throw new RuntimeException("notepad 의 studentId 와 session 의 studentId 비일치");
        }

        return savingNotepad.get();
    }

    /**
     * 주어진 studentId 로 entity 를 찾고, 찾아지는지 검증함
     *
     * @param studentId 주어진 studentId
     * @return
     */
    private StudentEntity findAndValidate(String studentId) {
        Optional<StudentEntity> register = studentRepository.findById(
                studentId
        );

        if (register.isEmpty()) {
            throw new RuntimeException("해당 student 가 없음!");
        }

        return register.get();
    }
}
