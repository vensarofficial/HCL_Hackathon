package com.example.payment.dto;
public class DraftResponse {
  public Long draftId;
  public String status;
  public DraftResponse(Long id, String status) { this.draftId = id; this.status = status; }
}
