package tw.com.iii.OceanCatHouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.com.iii.OceanCatHouse.model.OrdersBean;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersBean, Integer> {

    @Query(" FROM OrdersBean WHERE YEARWEEK(date_format(ordercreateon,'%Y-%m-%d')) = YEARWEEK(now())-1")
    List<OrdersBean> selectWeek();
    @Query(" FROM OrdersBean WHERE MONTH(date_format(ordercreateon,'%Y-%m-%d')) = MONTH(now())")
    List<OrdersBean> selectMonth();

    List<OrdersBean> findByUserid(Integer userId);

    @Query("from OrdersBean where userid=?1 order by ordercreateon DESC")
    List<OrdersBean> findByUseridOrderByOrdercreateonDesc(Integer userid);
}
