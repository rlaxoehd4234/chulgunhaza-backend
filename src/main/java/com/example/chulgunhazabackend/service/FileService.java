package com.example.chulgunhazabackend.service;

import com.example.chulgunhazabackend.domain.board.PostFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService {

    PostFile saveFile(MultipartFile file, int ord) throws IOException;

    List<PostFile> savePostFiles(List<MultipartFile> files) throws IOException;

    List<String> findPostFiles(List<PostFile> postFile) throws MalformedURLException;

}
