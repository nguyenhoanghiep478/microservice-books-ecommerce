With aggregated_book as (
    Select min(book.id) as id,
           book.name,
           sum(book.available_quantity) as total_quantity
    from
        book
    GROUP BY book.name
    Having count(*) > 1
)
update book
set available_quantity = aggr.total_quantity
from aggregated_book aggr
where book.id = aggr.id;


update book
set image = CASE
                when name like 'Chúng ta %' then 'Chúng ta đã ly hôn tập 21.jpg'
                when name like 'Thám tử %' then  'Thám tử lừng danh conan tập 22.jpg'
                else image
    end
where image = 'update later' and (name LIKE 'Chúng ta %' OR name LIKE 'Thám tử %');

with duplicate_book as (
    select id
    from book b
    where exists(
        select 1
        from book b2
        where b.name = b2.name
          and b.id > b2.id
    )
)
delete from book
where id in (select id from duplicate_book);