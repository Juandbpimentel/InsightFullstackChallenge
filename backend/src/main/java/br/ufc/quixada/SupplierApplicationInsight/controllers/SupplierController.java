package br.ufc.quixada.SupplierApplicationInsight.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import br.ufc.quixada.SupplierApplicationInsight.models.Supplier;
import br.ufc.quixada.SupplierApplicationInsight.repositories.SupplierRepository;
import br.ufc.quixada.SupplierApplicationInsight.types.dto.supplier.request.SupplierCreateRequestDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("supplier")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping(produces = "application/json",path="/")
    public ResponseEntity<List<Supplier>> listSuppliers() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @PostMapping(consumes = "application/json", produces = "application/json",path="/")
    public ResponseEntity<String> createSupplier(@RequestBody @Valid SupplierCreateRequestDTO body) throws Exception {
        try {
            Supplier supplier = supplierRepository.save(new Supplier(body.name(),body.CNPJ(), body.phone(), body.email(), body.address(),body.serviceOrProductProvided()));
            return ResponseEntity.ok("Supplier created");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error creating supplier, exception" + e.getMessage());
        }
    }

    @GetMapping(produces = "application/json",path="/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(consumes = "application/json", produces = "application/json",path="/{id}")
    public ResponseEntity<String> updateSupplier(@PathVariable String id,@RequestBody @Valid SupplierCreateRequestDTO body) {
        return ResponseEntity.ok("Supplier updated");
    }

    @DeleteMapping(produces = "application/json",path="/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable String id) {
        return ResponseEntity.ok("Supplier deleted");
    }

}
