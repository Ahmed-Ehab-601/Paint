package com.csed.paintapp.service.saveLoadService;


import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface ISave {
    void save(String path) throws IOException, JAXBException;
}
