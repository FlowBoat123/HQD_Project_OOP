# 📚 Dự án Quản lý Thư viện

## 📝 Giới thiệu
Đây là một ứng dụng quản lý thư viện được xây dựng nhằm cải thiện trải nghiệm người dùng trong việc mượn, trả, tra cứu sách và hỗ trợ quản lý thư viện một cách hiệu quả, tiện lợi.
<br><br>
### Tác giả:
1. Đầu Hồng Quang - 23020135
2. Nguyễn Trung Hiển - 23020063
3. Trương Quang Duy - 23020033
---
## ⚡ Tính năng chính

### **Giao diện Người dùng (User):**
1. **Hiển thị thư viện:**
   - Hiển thị các tủ sách như:
     - **Tủ gợi ý đọc**: Dựa trên thói quen và đánh giá sách của người dùng.
     ![image](/src/main/resources/images/UserRecommendationView.png)
     - **Tủ toàn bộ sách**: Liệt kê tất cả sách trong thư viện.
     ![image](/src/main/resources/images/UserDashBoardView.png)
   - Tìm kiếm sách bằng: 
     - Tên sách hoặc ISBN.
   - Giao diện trực quan, thân thiện và dễ sử dụng.
     ![image](/src/main/resources/images/UserBookView.png)

2. **Chức năng mượn/trả sách:**
   - Người dùng có thể:
     - **Borrow** bạn có thể mượn sách.
     ![image](/src/main/resources/images/BorrowSuccess.png)
     - **Waiting** nếu số lượng sách đã hết, bạn trong danh sách chờ. Hệ thống sẽ thông báo nếu sách yêu cầu được admin thêm vào thư viện.
     ![image](/src/main/resources/images/WaitingState.png)
     ![image](/src/main/resources/images/WaitingState2.png)
     - **Return** đánh dấu đã đọc xong và trả sách.
     ![image](/src/main/resources/images/ReturnSuccess.png)
     - **Completed** sách đã đọc xong.
     ![image](/src/main/resources/images/Completed.png)

3. **Tích hợp mã QR:**
   - Quét mã QR để đọc preview của sách.
     ![image](/src/main/resources/images/BookQRCode.png)
     
4. **Comment:**
   - Hãy để lại comment của bạn về điều bạn ấn tượng nhất với mỗi cuốn sách bạn đọc và mọi người đều có thể thấy cảm nhận của bạn về cuốn sách đó.
   ![image](/src/main/resources/images/AddComment.png)
5. **Profile**
    - Chỉnh sửa profile ca nhân dễ dàng
   ![image](/src/main/resources/images/UserProfileChange02.png)
---

### **Giao diện Người quản lý (Admin):**
1. **Quản lý tài liệu:**
   - Thêm, sửa, hoặc xóa tài liệu khỏi thư viện.
     ![image](/src/main/resources/images/AdminFindBook.png)
     ![image](/src/main/resources/images/AdminAddSucess.png)
     ![image](/src/main/resources/images/AddAPI.png)
   - Theo dõi lượng tài liệu hiện tại trong thư viện.

2. **Quản lý người dùng:**
   - Thêm hoặc xóa người dùng trong hệ thống.
     ![image](/src/main/resources/images/AddUser.png)
   - Theo dõi hoạt động mượn sách của người dùng.
     ![image](/src/main/resources/images/RemoveUser.png)
     ![image](/src/main/resources/images/AddSucces.jpg)
     ![image](/src/main/resources/images/AddError.png)
     
3. **Xử lý trường hợp lỗi:**
   - Thông báo lỗi.
     ![image](/src/main/resources/images/RemoveError.png)
   - Kiểm tra điều kiện khi mượn tài liệu.

---

## 🛠️ Công nghệ sử dụng

1. **Ngôn ngữ lập trình chính:**  
   - Java  
   - Sử dụng các tính năng lập trình hướng đối tượng (*OOP*) như trừu tượng hóa, kế thừa, đa hình.  
   - Tích hợp **Design Pattern: Singleton, Data Access Object** để tối ưu hóa và quản lý tài nguyên.

2. **Cơ sở dữ liệu:**  
   - SQLite: Quản lý và lưu trữ thông tin sách, người dùng và các hoạt động liên quan, lưu trữ local tại project .

3. **Kiểm thử:**  
   - JUnit: Kiểm tra các thành phần logic để đảm bảo ứng dụng hoạt động chính xác.

4. **API tích hợp:**  
   - **Google API**: Hỗ trợ tra cứu thông tin sách qua tiêu đề và mã ISBN.

5. **Tính năng nâng cao:**
   - Thiết kế đa luồng (**Multithreading**) giúp cải thiện hiệu suất và giảm thời gian chờ của người dùng.
---
## Biểu đồ lớp
![image](/src/main/resources/images/classdiagram.png)
Đơn giản hóa:
![image](/src/main/resources/images/meme.jpg)
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
   - Kết nối cơ sở dữ liệu SQLite.
   - Chạy chương trình bằng cách thực thi file chính (`LoginApp.java`).

### 3. **Tài khoản mặc định:**
   - **Người dùng (User):**
     - Tự tạo tài khoản và quản lý
   - **Người quản lý (Admin):**
     - Tài khoản: `admin`
     - Mật khẩu: `admin`

---

## 🛠️ Phát triển trong tương lai
1. Tích hợp hệ thống thông báo mượn/trả sách qua email.
2. Cải thiện thuật toán gợi ý sách dựa trên lịch sử mượn/trả.
3. Mở rộng giao diện quản lý báo cáo thống kê chi tiết cho Admin.
4. Hỗ trợ giao diện đa ngôn ngữ.
5. Cải thiện giao diện.
6. Đa dạng hóa các thể loại tài liệu.
---
