package com.chunjae.project05.biz;

import com.chunjae.project05.entity.FileDTO;

import java.util.List;

public interface FileDTOService {

    public List<FileDTO> fileListByPar(FileDTO fileDTO) throws Exception;
    public FileDTO fileByFno(int fno) throws Exception;
    public int filesInsert(FileDTO fileDTO) throws Exception;
    public int filesDelete(int fno) throws Exception;
    public int filesDeleteAll(int par) throws Exception;

}