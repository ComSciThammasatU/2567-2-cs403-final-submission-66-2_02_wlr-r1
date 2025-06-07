[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/w8H8oomW)
**<ins>Note</ins>: Students must update this `README.md` file to be an installation manual or a README file for their own CS403 projects.**

**รหัสโครงงาน:** 66-2_02_wlr-r1

**ชื่อโครงงาน (ไทย):** โมบายแอปพลิเคชันสำหรับหมู่บ้านจัดสรร (หมู่บ้านฉัตรหลวง16)

**Project Title (Eng):** Mobile application for housing developments (Chat Luang 16)

**อาจารย์ที่ปรึกษาโครงงาน:** ระบุชื่ออาจารย์ที่ปรึกษาโครงงานที่นี่ 

**ผู้จัดทำโครงงาน:** (โปรดเขียนข้อมูลผู้จัดทำโครงงานตามฟอร์แมตดังแสดงในตัวอย่างด้านล่าง)
1. นายกันย์ เจริญนิวาสสกุล  6209540019  kun.cha@dome.tu.ac.th

   
Manual / Instructions for your projects starts here !
# ขั้นตอนที่ 1: ติดตั้งโปรแกรมที่จำเป็น
# 1.ติดตั้ง Git
Git ใช้สำหรับดาวน์โหลดโค้ดจาก GitHub
- เข้าไปที่เว็บไซต์นี้: https://git-scm.com/downloads

- เลือกเวอร์ชันที่ตรงกับระบบปฏิบัติการของคุณ (Windows / macOS)

- ดาวน์โหลดและติดตั้ง (ให้กด Next ไปเรื่อย ๆ จนเสร็จ)

- เมื่อติดตั้งเสร็จ ลองเปิดโปรแกรม Command Prompt หรือ Git Bash

- พิมพ์คำสั่งนี้เพื่อตรวจสอบว่า Git ติดตั้งเรียบร้อยแล้ว: 
```
git --version
```
ถ้าเห็นเลขเวอร์ชัน เช่น git version 2.42.0 แสดงว่าติดตั้งสำเร็จ
#  2.ติดตั้ง Android Studio
Android Studio คือโปรแกรมหลักที่ใช้พัฒนาแอป Android

- เข้าไปที่: https://developer.android.com/studio

- กดปุ่ม Download Android Studio และติดตั้งตามขั้นตอน

- ในขั้นตอนการติดตั้ง จะมีให้ติดตั้ง Android SDK, AVD (Emulator), และ ตัวจำลองมือถือ → ให้ติ๊กติดตั้งทุกอย่าง

- เมื่อติดตั้งเสร็จแล้ว เปิด Android Studio ครั้งแรก ระบบจะโหลดข้อมูลเพิ่มเติม (ใช้เวลานานพอสมควร)

# กรณีถ้ามีแจ้งเตือน ติดตั้ง Java Development Kit (JDK)
ส่วนใหญ่ Android Studio มี JDK มาให้แล้ว ไม่ต้องติดตั้งเพิ่ม
แต่ถ้าเปิดโปรเจกต์แล้วขึ้น error ว่าไม่พบ JDK ให้ทำตามนี้
- เข้าไปที่: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

- ดาวน์โหลดเวอร์ชันที่ตรงกับระบบของคุณ เช่น Windows x64

- ติดตั้งตามปกติ แล้วลองรัน Android Studio ใหม่
# ขั้นตอนที่ 2: ดาวน์โหลดโปรเจกต์จาก GitHub
1. เปิด Git Bash หรือ Command Prompt
2. พิมพ์คำสั่งนี้ (แทนที่ URLของโปรเจกต์ ด้วยลิงก์จริงจาก GitHub):
```
git clone https://github.com/ComSciThammasatU/2567-2-cs403-final-submission-66-2_02_wlr-r1.git
```
3. รอให้โหลดโค้ดทั้งหมดเสร็จ แล้วพิมพ์คำสั่งนี้เพื่อลงไปในโฟลเดอร์โปรเจกต์:
```
 cd 2567-2-cs403-final-submission-66-2_02_wlr-r1
```
#  ขั้นตอนที่ 3: เปิดโปรเจกต์ใน Android Studio
1. เปิด Android Studio

2. คลิก "Open" หรือ "Open an Existing Project"

3. เลือกโฟลเดอร์โปรเจกต์ที่เพิ่ง clone มา

4. รอให้ Android Studio โหลดและ Sync Gradle (อาจใช้เวลาหลายนาที)

- ถ้ามีแจ้งเตือนให้กด “Accept” หรือ “Install Missing SDK/Plugin” ให้กดตกลงทั้งหมด
- ถ้าไม่ขึ้นอะไร ให้ไปที่เมนู File > Sync Project with Gradle Files
# ขั้นตอนที่ 4: รันโปรเจกต์ในเครื่อง
ถ้าใช้ Emulator (มือถือจำลอง)
1. ไปที่เมนู Tools > Device Manager
2. สร้าง Virtual Device ขึ้นมา เช่น Pixel 5 หรือ Nexus 6
3. กด Play (▶️) เพื่อเปิด Emulator

ถ้าใช้มือถือจริง

1. เปิดโหมดนักพัฒนาในโทรศัพท์ → ไปที่ Settings(การตั้งค่า) > About phone(เกี่ยวกับโทรศัพท์) > Tap “Build number” 7 ครั้ง
2. กลับไปที่ Settings(การตั้งค่า) > Developer options(ทางเลือกผู้พัฒนา) > เปิด USB Debugging
3. เสียบสาย USB กับคอมพิวเตอร์
4. บนมือถือจะมีแจ้งเตือน “Allow USB debugging?” → ให้กด “Allow”

# สุดท้าย: รันแอป
1. ใน Android Studio ให้คลิกปุ่ม ▶️ "Run"
2. เลือกอุปกรณ์ที่ต้องการรัน (Emulator หรือ มือถือจริง)
3. ระบบจะทำการ Build และติดตั้งแอป
4. รอจนกว่าจะเห็นแอปเปิดขึ้นมา
# หากพบข้อผิดพลาดให้ลองสังเกตตามนี้
- ถ้ามี Error เกี่ยวกับ Gradle → ไปที่ File > Invalidate Caches / Restart
- ถ้ามี Error ว่า SDK ไม่ตรง → ไปที่ Tools > SDK Manager แล้วเลือก SDK ที่ตรงกับโปรเจกต์
- ถ้าเปิดไม่ขึ้น → ตรวจสอบว่าไฟล์ build.gradle และ AndroidManifest.xml อยู่ในที่ถูกต้อง





