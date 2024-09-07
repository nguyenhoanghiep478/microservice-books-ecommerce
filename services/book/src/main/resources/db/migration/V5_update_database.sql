update book
set image = case
    when name like 'Khoa %' then 'Khoa học viễn tường tập 15.jpg'
    when name like 'Tiểu %' then 'Tiểu thuyết tập 15.jpg'
    when name like 'Truyện %' then 'Truyện tranh tập 1.jpg'
end
where image ='update later' and (name like 'Khoa %' or name like 'Tiểu %' or name like 'Truyện %')