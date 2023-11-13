package com.chunjae.project05.biz;

import com.chunjae.project05.entity.FileDTO;
import com.chunjae.project05.persistence.FileDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDTOServiceImpl implements FileDTOService {

    @Autowired
    private FileDTOMapper fileDTOMapper;

    @Override
    public List<FileDTO> fileListByPar(FileDTO fileDTO) throws Exception {
        return fileDTOMapper.fileListByPar(fileDTO);
    }

    @Override
    public FileDTO fileByFno(int fno) throws Exception {
        return fileDTOMapper.fileByFno(fno);
    }

    @Override
    public int filesInsert(FileDTO fileDTO) throws Exception {
        return fileDTOMapper.filesInsert(fileDTO);
    }

    @Override
    public int filesDelete(int fno) throws Exception {
        return fileDTOMapper.filesDelete(fno);
    }

    @Override
    public int filesDeleteAll(int par) throws Exception {
        return fileDTOMapper.filesDeleteAll(par);
    }

}