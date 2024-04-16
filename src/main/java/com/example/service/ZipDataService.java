package com.example.service;

import java.io.File;
import java.io.IOException;

public interface ZipDataService {
    ZipData processZip(File data) throws IOException;
}
