package service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class ZipDataServiceImpl implements ZipDataService {

    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String PIPS_FOLDER_NAME = "pips";
    public static final String POLICIES_FOLDER_NAME = "policies";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ZipDataServiceImpl.class);

    @Override
    public ZipData processZip(File zip) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Processing zip {}", zip.getName());
        }
        ZipData zipData = new ZipData();
        zipData.setZipFileName(zip.getName());
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    if (entry.getName().startsWith(PIPS_FOLDER_NAME)) {
                        zipData.addPipData(new String(zis.readAllBytes()));
                    }
                    if (entry.getName().startsWith(POLICIES_FOLDER_NAME)) {
                        zipData.addPolicyData(new String(zis.readAllBytes()));
                    }
                    if (entry.getName().endsWith(CONFIG_FILE_NAME)) {
                        zipData.setConfigData(new String(zis.readAllBytes()));
                    }
                }
            }
        }
        return zipData;
    }
}
