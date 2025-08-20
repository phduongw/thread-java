## Thread State:

Bao gồm 3 trạng thái:
```
- RUNNABLE: Thread vẫn đang được chạy.
- TERMINATED: Thread đã được ngắt (interrupted) hoặc là hoàn thành (completed).
- TIMED_WAITING: Thread bị sleep.
```
- Với method join() thì sẽ bắt luồng hiện tại chờ thread gọi join() kết thúc thì mới được thực thi tiếp các dòng code bên dưới.
- Thực tế, nếu chỉ khai báo các thread thông thường và chạy chúng thì nó chỉ đang xử lí bởi 1 CPU core và được phân chia thời gian xử lí bởi 1 CPU Core. Cái này gọi là Time Slicing. 1 CPU sẽ phân bổ thời gian ngắn để chạy từng thread 1 chứ không phải là mỗi thread được xử lí bởi 1 CPU core. Dẫn đến việc ta cảm giác như các thread đang được xử lí cùng 1 lúc nhưng thực ra là không phải.

