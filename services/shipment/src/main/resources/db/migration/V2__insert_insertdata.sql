DO
$$
    BEGIN
        FOR i IN 1..86 LOOP
                INSERT INTO shipment_details (
                    origin_address_id,
                    destination_address_id,
                    distance,
                    current_address_id,
                    total_fee,
                    tracking_number,
                    status,
                    created_date,
                    last_modified_date,
                    shipment_service_id
                )
                VALUES (
                           1,
                           2,
                           3.7,
                           2,
                           3.7 * (
                               SELECT cost_per_km
                               FROM shipment_service
                               WHERE id = CASE WHEN i % 2 = 0 THEN 1 ELSE 2 END
                           ),
                           random() * 1000000,
                           'SHIPPED',
                           CURRENT_TIMESTAMP,
                           CURRENT_TIMESTAMP,
                           CASE WHEN i % 2 = 0 THEN 1 ELSE 2 END
                       );
            END LOOP;
    END
$$;
