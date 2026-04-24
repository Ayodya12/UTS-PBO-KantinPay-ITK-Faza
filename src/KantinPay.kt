// 1. Class Mahasiswa dengan Data Hiding
class Mahasiswa(val nama: String, private val pin: String, initialSaldo: Double) {
    var saldo: Double = initialSaldo
        private set

    fun validasiPin(inputPin: String): Boolean {
        return this.pin == inputPin
    }

    fun kurangiSaldo(nominal: Double): Boolean {
        if (this.saldo >= nominal) {
            this.saldo -= nominal
            return true
        }
        return false
    }
}

// 2. Class Menu dengan Data Hiding
class Menu(val namaMenu: String, val harga: Double, initialStok: Int) {
    var stok: Int = initialStok
        private set

    fun isTersedia(): Boolean {
        return this.stok > 0
    }

    fun kurangiStok() {
        if (stok > 0) {
            stok--
        }
    }
}

// 3. Class StandMakanan
class StandMakanan(val namaStand: String) {
    fun prosesPesanan(mhs: Mahasiswa, menu: Menu, inputPin: String) {
        println("\n--- Memproses Pesanan ${menu.namaMenu} ---")

        if (!mhs.validasiPin(inputPin)) {
            println("[ERROR] Transaksi Gagal: PIN salah!")
            return
        }

        if (!menu.isTersedia()) {
            println("[ERROR] Transaksi Gagal: Stok ${menu.namaMenu} habis!")
            return
        }

        if (mhs.kurangiSaldo(menu.harga)) {
            menu.kurangiStok()
            println("[SUKSES] Pembayaran berhasil!")
            println("=> Saldo sisa: Rp${mhs.saldo}")
            println("=> Stok ${menu.namaMenu} sisa: ${menu.stok}")
        } else {
            println("[ERROR] Transaksi Gagal: Saldo tidak mencukupi!")
            println("=> Saldo kamu: Rp${mhs.saldo} | Harga: Rp${menu.harga}")
        }
    }
}

// 4. MAIN FUNCTION
fun main() {
    // Inisialisasi Data Awal
    val faza = Mahasiswa("Faza", "123456", 50000.0)
    val ayamGeprek = Menu("Ayam Geprek", 20000.0, 4)
    val esTeh = Menu("Es Teh Jumbo", 5000.0, 5)
    val kantin = StandMakanan("Kantin Pojok ITK")

    var isAppRunning = true

    // Loop agar program terus berjalan sampai dipilih "Keluar"
    while (isAppRunning) {
        println("\n==============================")
        println("Selamat datang di ${kantin.namaStand}")
        println("Pengguna: ${faza.nama} | Saldo: Rp${faza.saldo}")
        println("==============================")
        println("Menu Hari Ini:")
        println("1. ${ayamGeprek.namaMenu} (Rp${ayamGeprek.harga}) - Sisa Stok: ${ayamGeprek.stok}")
        println("2. ${esTeh.namaMenu} (Rp${esTeh.harga}) - Sisa Stok: ${esTeh.stok}")
        println("3. Keluar dari Aplikasi")
        print("Masukkan pilihan Anda (1/2/3): ")

        // Membaca inputan keyboard di terminal
        val pilihan = readlnOrNull() ?: ""

        when (pilihan) {
            "1" -> {
                print("Masukkan PIN Anda untuk bayar: ")
                val pin = readlnOrNull() ?: ""
                kantin.prosesPesanan(faza, ayamGeprek, pin)
            }
            "2" -> {
                print("Masukkan PIN Anda untuk bayar: ")
                val pin = readlnOrNull() ?: ""
                kantin.prosesPesanan(faza, esTeh, pin)
            }
            "3" -> {
                println("\nTerima kasih telah menggunakan KantinPay! Semoga kenyang!")
                isAppRunning = false // Mematikan loop
            }
            else -> {
                println("\n[Peringatan] Pilihan tidak valid. Silakan ketik angka 1, 2, atau 3.")
            }
        }
    }
}