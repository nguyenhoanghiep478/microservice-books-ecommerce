package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.application.model.OrderSearchCriteria;
import com.bookms.order.application.usecase.impl.FindOrdersUseCase;
import com.bookms.order.application.usecase.IFindOrderUseCase;
import com.bookms.order.application.usecase.impl.FindSpecialFieldUseCase;
import com.bookms.order.application.usecase.impl.FindTopSalesUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.DTO.ChartDTO;
import com.bookms.order.interfaceLayer.DTO.TopSaleDTO;
import com.bookms.order.interfaceLayer.service.IFindOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FindOrderService implements IFindOrderService {
    private final IFindOrderUseCase findOrderUseCase;
    private final FindTopSalesUseCase findTopSalesUseCase;
    private final FindSpecialFieldUseCase findSpecialFieldUseCase;

    @Override
    public Orders findById(int id) {
        Criteria fieldId = Criteria.builder()
                .key("id")
                .operator("=")
                .value(id)
                .build();
        return findOrderUseCase.execute(List.of(fieldId)).get(0);
    }

    @Override
    public Orders findByOrderNumber(Long orderNumber) {
        Criteria fieldOrderNumber = Criteria.builder()
                .key("orderNumber")
                .operator("=")
                .value(orderNumber)
                .build();
        return findOrderUseCase.execute(List.of(fieldOrderNumber)).get(0);
    }

    @Override
    public List<Orders> findAll() {
        return findOrderUseCase.execute(null);
    }

    @Override
    public List<Orders> findLatest(int amount) {
        Criteria fieldCreatedDate = Criteria.builder()
                .key("createdDate")
                .operator("Order By")
                .build();
        Criteria fieldAmount = Criteria.builder()
                .operator("GET")
                .value(amount)
                .build();
        Criteria desc = Criteria.builder()
                .operator("DESC")
                .build();
        return findOrderUseCase.execute(List.of(fieldCreatedDate, fieldAmount,desc));
    }

    @Override
    public List<TopSaleDTO> getTopSales() {
        return findTopSalesUseCase.execute().stream()
                .map(item -> {
                    TopSaleDTO dto = new TopSaleDTO();
                    dto.setBookId(item);
                    return dto;
                }).toList();
    }

    @Override
    public List<ChartDTO> getChartOrderInWeek() {
        Criteria searchBySQl = Criteria.builder()
                .sql("select EXTRACT(DOW FROM o.created_date) as day_of_week,SUM(o.total_price) as total_payment\n" +
                        "from orders o\n" +
                        "where o.created_date >= DATE_TRUNC('week',NOW()) and o.status = 'COMPLETED'\n" +
                        "AND o.created_date < DATE_TRUNC('week',NOW())+INTERVAL '1 week'\n" +
                        "GROUP BY EXTRACT(DOW FROM o.created_date)")
                .build();

        return (List<ChartDTO>) findSpecialFieldUseCase.execute(List.of(searchBySQl));
    }


    @SafeVarargs
    private List<Criteria> getCriteria(Map<String,Object>... fields) {
        List<Criteria> criteriaList = new ArrayList<>();
        for (var field : fields) {

        }
        return criteriaList;
    }

}
