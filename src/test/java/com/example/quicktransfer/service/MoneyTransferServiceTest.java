/*
 * package com.example.quicktransfer.service;
 * 
 * import static org.junit.jupiter.api.Assertions.assertDoesNotThrow; import
 * static org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.mockito.ArgumentMatchers.any; import static org.mockito.Mockito.when;
 * 
 * import java.math.BigDecimal; import java.util.Optional;
 * 
 * import org.junit.jupiter.api.Test; import org.mockito.InjectMocks; import
 * org.mockito.Mock;
 * 
 * import com.example.quicktransfer.dto.CreateTransferRequest; import
 * com.example.quicktransfer.dto.TransferResponse; import
 * com.example.quicktransfer.dto.UpdateTransferStatusRequest; import
 * com.example.quicktransfer.entity.Customer; import
 * com.example.quicktransfer.entity.MoneyTransfer; import
 * com.example.quicktransfer.enums.TransferStatus; import
 * com.example.quicktransfer.exceptions.BussinessValidationException; import
 * com.example.quicktransfer.exceptions.InvalidStatusTransitionException; import
 * com.example.quicktransfer.exceptions.ResourceNotFoundException; import
 * com.example.quicktransfer.mapper.TransferMapper; import
 * com.example.quicktransfer.repository.CustomerRepository; import
 * com.example.quicktransfer.repository.MoneyTransferRepository;
 * 
 * public class MoneyTransferServiceTest {
 * 
 * @Mock private MoneyTransferRepository moneyTransferRepository;
 * 
 * @Mock private CustomerRepository customerRepository;
 * 
 * @Mock private TransferMapper transferMapper;
 * 
 * @Mock private MoneyTransferService moneyTransferService;
 * 
 * @InjectMocks private CustomerServiceImpl customerService;
 * 
 * @Test void createTransfer_ShouldCreateTransferSuccessfully() {
 * 
 * CreateTransferRequest request = new CreateTransferRequest();
 * 
 * request.setCustomerId(1L);
 * request.setTransferAmount(BigDecimal.valueOf(500));
 * 
 * Customer customer = new Customer(); customer.setCustomerId(1L);
 * customer.setActiveFlag(true);
 * 
 * MoneyTransfer transfer = new MoneyTransfer();
 * 
 * TransferResponse response =
 * TransferResponse.builder().transactionId(1L).build();
 * 
 * when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
 * 
 * when(transferMapper.toEntity(request, customer)).thenReturn(transfer);
 * 
 * when(moneyTransferRepository.save(any())).thenReturn(transfer);
 * 
 * when(transferMapper.toResponse(any())).thenReturn(response);
 * 
 * TransferResponse result = moneyTransferService.createTransfer(request);
 * 
 * assertNotNull(result); }
 * 
 * @Test void createTransfer_ShouldThrowException_WhenCustomerNotFound() {
 * 
 * CreateTransferRequest request = new CreateTransferRequest();
 * 
 * request.setCustomerId(999L);
 * 
 * when(customerRepository.findById(999L)).thenReturn(Optional.empty());
 * 
 * assertThrows(ResourceNotFoundException.class, () ->
 * moneyTransferService.createTransfer(request)); }
 * 
 * @Test void createTransfer_ShouldThrowException_WhenCustomerInactive() {
 * 
 * CreateTransferRequest request = new CreateTransferRequest();
 * 
 * request.setCustomerId(1L);
 * 
 * Customer customer = new Customer(); customer.setActiveFlag(false);
 * 
 * when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
 * 
 * assertThrows(BussinessValidationException.class, () ->
 * moneyTransferService.createTransfer(request)); }
 * 
 * @Test void getTransferById_ShouldThrowException() {
 * 
 * when(moneyTransferRepository.findById(100L)).thenReturn(Optional.empty());
 * 
 * assertThrows(ResourceNotFoundException.class, () ->
 * moneyTransferService.getTransferById(100L)); }
 * 
 * @Test void updateStatus_ShouldAllowCreatedToValidated() {
 * 
 * MoneyTransfer transfer = new MoneyTransfer();
 * 
 * transfer.setTransferStatus(TransferStatus.CREATED);
 * 
 * UpdateTransferStatusRequest request = new UpdateTransferStatusRequest();
 * 
 * request.setStatus(TransferStatus.VALIDATED);
 * 
 * when(moneyTransferRepository.findById(1L)).thenReturn(Optional.of(transfer));
 * 
 * when(moneyTransferRepository.save(any())).thenReturn(transfer);
 * 
 * assertDoesNotThrow(() -> moneyTransferService.updateTransferStatus(1L,
 * request)); }
 * 
 * @Test void updateStatus_ShouldThrowException_ForInvalidTransition() {
 * 
 * MoneyTransfer transfer = new MoneyTransfer();
 * 
 * transfer.setTransferStatus(TransferStatus.COMPLETED);
 * 
 * UpdateTransferStatusRequest request = new UpdateTransferStatusRequest();
 * 
 * request.setStatus(TransferStatus.FAILED);
 * 
 * when(moneyTransferRepository.findById(1L)).thenReturn(Optional.of(transfer));
 * 
 * assertThrows(InvalidStatusTransitionException.class, () ->
 * moneyTransferService.updateTransferStatus(1L, request)); }
 * 
 * }
 */