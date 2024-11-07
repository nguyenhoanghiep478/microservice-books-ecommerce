update book
set image = case
                when name like 'Khoa %' then 'Khoa học viễn tưởng tập 15.jpg'
    end
where name like 'Khoa %'