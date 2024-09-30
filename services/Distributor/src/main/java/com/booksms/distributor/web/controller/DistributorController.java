package com.booksms.distributor.web.controller;

import com.booksms.distributor.interfaceLayer.DTO.DistributorDTO;
import com.booksms.distributor.interfaceLayer.DTO.ResponseDTO;
import com.booksms.distributor.interfaceLayer.service.IDistributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/distributor")
@RequiredArgsConstructor
public class DistributorController {
    private final IDistributorService service;

    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll() {
        //get listDTO
        List<DistributorDTO> results = service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> GetById(@PathVariable int id) {
        //get bookDTO
        DistributorDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody DistributorDTO request) {
        DistributorDTO result = service.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("createDistributorSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @PutMapping("{id}/update-license/{licenseId}")
    public ResponseEntity<?> UpdateLicense(@RequestBody DistributorDTO request,@PathVariable  int id, @PathVariable int licenseId) {
        DistributorDTO result = service.updateLicense(id,licenseId,request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("createDistributorSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
}
