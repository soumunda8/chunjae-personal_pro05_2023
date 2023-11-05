package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDTOMapper {

    List<FileDTO> fileListByPar(FileDTO fileDTO) throws Exception;
    FileDTO fileByFno(int fno) throws Exception;
    int filesInsert(FileDTO fileDTO) throws Exception;
    int filesDelete(int fno) throws Exception;
    int filesDeleteAll(int parNo) throws Exception;

}