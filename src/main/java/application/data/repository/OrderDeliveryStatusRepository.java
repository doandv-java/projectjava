package application.data.repository;

import application.data.model.DeliveryStatus;
import application.data.model.OrderDeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDeliveryStatusRepository extends JpaRepository<OrderDeliveryStatus, Integer> {
    @Query(value = "select * from dbo_order_delivery_status where order_delivery_status_id=:orderDeliveryStatusId", nativeQuery = true)
    OrderDeliveryStatus findOne(@Param("orderDeliveryStatusId") Integer orderDeliveryStatusId);
}
