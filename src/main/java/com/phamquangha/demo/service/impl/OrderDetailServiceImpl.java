package com.phamquangha.demo.service.impl;

import com.phamquangha.demo.entity.OrderDetail;
import com.phamquangha.demo.repository.OrderDetailRepository;
import com.phamquangha.demo.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    @Override
    public List<OrderDetail> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderDetail> getByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail create(OrderDetail orderDetail) {
        orderDetail.setTotalPrice(orderDetail.getQuantity() * orderDetail.getUnitPrice());
        return repository.save(orderDetail);
    }

    @Override
    public OrderDetail update(Long id, OrderDetail orderDetail) {
        OrderDetail existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
        existing.setQuantity(orderDetail.getQuantity());
        existing.setUnitPrice(orderDetail.getUnitPrice());
        existing.setTotalPrice(orderDetail.getQuantity() * orderDetail.getUnitPrice());
        existing.setOrder(orderDetail.getOrder());
        existing.setProduct(orderDetail.getProduct());
        // updated_at sẽ tự động được cập nhật trong @PreUpdate
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
