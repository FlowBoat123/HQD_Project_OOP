# 📚 Dự án Quản lý Thư viện

## 📝 Giới thiệu
Đây là một ứng dụng quản lý thư viện được xây dựng bởi nhóm 3 sinh viên nhằm cải thiện trải nghiệm người dùng trong việc mượn, trả, tra cứu sách và hỗ trợ quản lý thư viện một cách hiệu quả, tiện lợi.

---

## ⚡ Tính năng chính

### **Giao diện Người dùng (User):**
1. **Hiển thị thư viện:**
   - Hiển thị các tủ sách như:
     - **Tủ gợi ý đọc**: Dựa trên thói quen và đánh giá sách của người dùng.
     - **Tủ toàn bộ sách**: Liệt kê tất cả sách trong thư viện.
   - Tìm kiếm sách bằng: 
     - Tên sách, tác giả, thể loại hoặc ISBN.
   - Giao diện trực quan, thân thiện và dễ sử dụng.

2. **Chức năng mượn/trả sách:**
   - Người dùng có thể:
     - **Unread** bạn có thể mượn sách.
     - **Chờ đợi** nếu số lượng sách đã hết, bạn trong danh sách chờ.
     - **Chờ đợi** nếu số lượng sách đã được thêm ứng dụng sẽ có request trong lần tiếp theo bạn đăng nhập rằng bạn muốn mượn cuốn sách này nữa hay không.
     - **Return** nếu bạn muốn trả sách
     - **Completed** nếu bạn đã trả sách  

3. **Tích hợp mã QR:**
   - Quét mã QR để lấy thông tin.
     
4. **Comment:**
   - Hãy để lại comment của bạn về điều bạn ấn tượng nhất với mỗi cuốn sách bạn đọc và mọi người đều có thể thấy cảm nhận của bạn về cuốn sách đó.

---

### **Giao diện Người quản lý (Admin):**
1. **Quản lý sách:**
   - Thêm, sửa, hoặc xóa sách khỏi thư viện.
   - Theo dõi lượng sách hiện tại trong thư viện.

2. **Quản lý người dùng:**
   - Thêm hoặc xóa người dùng trong hệ thống.
   - Theo dõi hoạt động mượn sách của người dùng.

---

## 🛠️ Công nghệ sử dụng

1. **Ngôn ngữ lập trình chính:**  
   - Java  
   - Sử dụng các tính năng lập trình hướng đối tượng (*OOP*) như trừu tượng hóa, kế thừa, đa hình, trừu tượng hóa.  
   - Tích hợp **Design Pattern: Singleton** để tối ưu hóa quản lý tài nguyên.

2. **Cơ sở dữ liệu:**  
   - SQLite: Quản lý và lưu trữ thông tin sách, người dùng và các hoạt động liên quan.

3. **Kiểm thử:**  
   - JUnit: Kiểm tra các thành phần logic để đảm bảo ứng dụng hoạt động chính xác.

4. **API tích hợp:**  
   - **Google API**: Hỗ trợ tra cứu thông tin sách qua mã ISBN.

5. **Tính năng nâng cao:**
   - Thiết kế đa luồng (**Multithreading**) giúp cải thiện hiệu suất và giảm thời gian chờ của người dùng.

---

## 🎯 Mục tiêu
- **Người dùng:**
  - Đơn giản hóa việc tra cứu, mượn và trả sách.
  - Cải thiện tương tác xã hội qua việc xem đánh giá và bình luận sách từ người dùng khác.
  - Tích hợp mã QR để tăng tốc độ thao tác.
- **Người quản lý thư viện:**
  - Quản lý thư viện dễ dàng và trực quan hơn.
  - Theo dõi và cập nhật số lượng sách trong thư viện.

---

## 🚀 Cách sử dụng

### 1. **Yêu cầu hệ thống:**
   - **Java Development Kit (JDK)**: Phiên bản 8 trở lên.
   - SQLite: Đã cài đặt sẵn.
   - Kết nối Internet để sử dụng Google API.

### 2. **Hướng dẫn cài đặt:**
   - Clone hoặc tải dự án từ repository.
   - Import dự án vào IDE (IntelliJ IDEA, Eclipse, NetBeans).
   - Kết nối cơ sở dữ liệu SQLite (file `.db` được cấu hình sẵn trong mã nguồn).
   - Chạy chương trình bằng cách thực thi file chính (`Main.java`).

### 3. **Tài khoản mặc định:**
   - **Người dùng (User):**
     - Tài khoản: `duy`
     - Mật khẩu: `duy`
   - **Người quản lý (Admin):**
     - Tài khoản: `admin`
     - Mật khẩu: `admin`

---

## 🛠️ Phát triển trong tương lai
1. Tích hợp hệ thống thông báo mượn/trả sách qua email.
2. Cải thiện thuật toán gợi ý sách dựa trên lịch sử mượn/trả.
3. Mở rộng giao diện quản lý báo cáo thống kê chi tiết cho Admin.
4. Hỗ trợ giao diện đa ngôn ngữ.

---

## 📷 Hình ảnh minh họa


---
