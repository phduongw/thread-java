## Thread State:

Bao gồm 3 trạng thái:
```
- RUNNABLE: Thread vẫn đang được chạy.
- TERMINATED: Thread đã được ngắt (interrupted) hoặc là hoàn thành (completed).
- TIMED_WAITING: Thread bị sleep.
```
- Với method join() thì sẽ bắt luồng hiện tại chờ thread gọi join() kết thúc thì mới được thực thi tiếp các dòng code bên dưới.
- Thực tế, nếu chỉ khai báo các thread thông thường và chạy chúng thì nó chỉ đang xử lí bởi 1 CPU core và được phân chia thời gian xử lí bởi 1 CPU Core. Cái này gọi là Time Slicing. 1 CPU sẽ phân bổ thời gian ngắn để chạy từng thread 1 chứ không phải là mỗi thread được xử lí bởi 1 CPU core. Dẫn đến việc ta cảm giác như các thread đang được xử lí cùng 1 lúc nhưng thực ra là không phải.

## Từ khóa [VOLATILE]():
- Luôn luôn ép CPU của thread phải load lại giá trị của biến từ main thread. 
- Hiệu quả: Sẽ giúp cho dữ liệu giữa các luồng luôn được nhất quán và đồng bộ.
- [Memory Consistency Errors](): 
  - Tức là hệ thống vận hành có thể đọc từ biến heap (Ram) và tạo 1 bản sao chép giá trị rồi lưu vào bộ nhớ đệm của riêng thread đó.
  - Suy ra, mỗi thread đều có 1 nơi lưu trữ bộ nhớ nhỏ và nhanh của riêng nó để nắm giữ bản copy riêng của giá trị được chia sẻ từ nguồn (main memory)
  - Với hiện tượng caching này, những sự thay đổi các giá trị chung được chia sẻ sẽ không được phản ánh hoặc xuất hiện ngay lập tức trong vùng nhớ heap (main memory). Thay vào đó là nó sẽ được cập nhật trước vào vùng nhớ cache của mỗi luồng.
## Từ khóa [SYNCHRONIZED]():
- Đây là từ khóa được áp dụng cho cấp độ method.
- Khi áp dụng từ khóa này, nếu nhiều luồng cùng tác động vào 1 object, và thực thi các hàm synchronize. Thì luồng nào thực thi hàm synchronize trước sẽ sẽ được ưu tiên xử lí, các luồng gọi các hàm synchronize sau sẽ bị tạm dừng cho đến khi luồng trước đó thực thi xong với object đó.
- Khi [method SYNCHRONIZED]() xuất hiện trong 1 object, nó đảm bảo rằng trạng thái của object luôn được nhìn thấy (nghĩa là khi object được update) bởi nhiều luồng khác.
## Deadlock:
- Deadlock thường xảy ra khi ta có 2 thread trở lên truy cập vào nhiều tài nguyên được chia sẻ. 