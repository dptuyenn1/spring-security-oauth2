package com.dev.demo.mappers;

public interface Mapper<Model, DTO> {

    Model mapFromDTOToModel(DTO dto);

    DTO mapFromModelToDTO(Model model);
}
