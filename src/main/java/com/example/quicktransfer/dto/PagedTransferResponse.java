package com.example.quicktransfer.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagedTransferResponse {

    private List<TransferSearchResponse> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
}