package com.chunjae.project05.biz;

import com.chunjae.project05.entity.FileDTO;
import com.chunjae.project05.persistence.FileDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDTOService {

    @Autowired
    private FileDTOMapper fileDTOMapper;

    public List<FileDTO> fileListByPar(FileDTO fileDTO) throws Exception {
        return fileDTOMapper.fileListByPar(fileDTO);
    }

    public FileDTO fileByFno(int fno) throws Exception {
        return fileDTOMapper.fileByFno(fno);
    }

    public int filesInsert(FileDTO fileDTO) throws Exception {
        return fileDTOMapper.filesInsert(fileDTO);
    }

    public int filesDelete(int fno) throws Exception {
        return fileDTOMapper.filesDelete(fno);
    }

    public int filesDeleteAll(int par) throws Exception {
        return fileDTOMapper.filesDeleteAll(par);
    }

}