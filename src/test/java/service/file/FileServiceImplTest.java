package service.file;

import dto.FileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.file.FileRepository;
import repository.storage.FileStorage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileStorage fileStorage;

    @InjectMocks
    private FileServiceImpl fileService;

    private FileDTO testFileDTO;
    private InputStream testInputStream;

    @BeforeEach
    void setUp() {
        testFileDTO = new FileDTO();
        testFileDTO.setId(1L);
        testFileDTO.setName("test.txt");
        testFileDTO.setFilePath("/uploads/test.txt");

        testInputStream = new ByteArrayInputStream("test content".getBytes());
    }

    @Test
    void create_ShouldReturnSavedFile_WhenSuccessful() {
        // given
        when(fileStorage.saveFile(any(InputStream.class), anyString())).thenReturn(testFileDTO);
        when(fileRepository.save(any(FileDTO.class))).thenReturn(Optional.of(testFileDTO));

        // when
        FileDTO result = fileService.create(testInputStream, "test.txt");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("test.txt");
        verify(fileStorage, times(1)).saveFile(testInputStream, "test.txt");
        verify(fileRepository, times(1)).save(testFileDTO);
    }

    @Test
    void create_ShouldReturnNull_WhenRepositoryFails() {
        // given
        when(fileStorage.saveFile(any(InputStream.class), anyString())).thenReturn(testFileDTO);
        when(fileRepository.save(any(FileDTO.class))).thenReturn(Optional.empty());

        // when
        FileDTO result = fileService.create(testInputStream, "test.txt");

        // then
        assertThat(result).isNull();
        verify(fileStorage, times(1)).saveFile(testInputStream, "test.txt");
        verify(fileRepository, times(1)).save(testFileDTO);
    }

    @Test
    void getById_ShouldReturnFileWithContent_WhenExists() {
        // given
        byte[] content = "file content".getBytes();
        testFileDTO.setFileContent(content);

        when(fileRepository.getById(1L)).thenReturn(Optional.of(testFileDTO));
        when(fileStorage.getFile(anyString())).thenReturn(content);

        // when
        FileDTO result = fileService.getById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFileContent()).isEqualTo(content);
        verify(fileRepository, times(1)).getById(1L);
        verify(fileStorage, times(1)).getFile(testFileDTO.getFilePath());
    }

    @Test
    void getById_ShouldReturnNull_WhenNotExists() {
        // given
        when(fileRepository.getById(999L)).thenReturn(Optional.empty());

        // when
        FileDTO result = fileService.getById(999L);

        // then
        assertThat(result).isNull();
        verify(fileRepository, times(1)).getById(999L);
        verify(fileStorage, never()).getFile(anyString());
    }

    @Test
    void delete_ShouldReturnTrue_WhenFileExistsAndDeleted() {
        // given
        when(fileRepository.getById(1L)).thenReturn(Optional.of(testFileDTO));
        when(fileStorage.deleteFile(anyString())).thenReturn(true);
        when(fileRepository.delete(any(FileDTO.class))).thenReturn(true);

        // when
        boolean result = fileService.delete(1L);

        // then
        assertTrue(result);
        verify(fileStorage, times(1)).deleteFile(testFileDTO.getFilePath());
        verify(fileRepository, times(1)).delete(testFileDTO);
    }

    @Test
    void delete_ShouldReturnFalse_WhenFileNotExists() {
        // given
        when(fileRepository.getById(999L)).thenReturn(Optional.empty());

        // when
        boolean result = fileService.delete(999L);

        // then
        assertFalse(result);
        verify(fileStorage, never()).deleteFile(anyString());
        verify(fileRepository, never()).delete(any(FileDTO.class));
    }

    @Test
    void getAll_ShouldReturnListOfFiles() {
        // given
        List<FileDTO> files = Arrays.asList(testFileDTO, new FileDTO());
        when(fileRepository.getAll()).thenReturn(files);

        // when
        List<FileDTO> result = fileService.getAll();

        // then
        assertThat(result).hasSize(2);
        verify(fileRepository, times(1)).getAll();
    }
}