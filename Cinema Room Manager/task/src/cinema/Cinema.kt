package cinema
import java.lang.Exception
import java.util.*
fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    println("Enter the number of rows:")
    val rows = scanner.nextInt()
    val firstHalf = rows / 2
    println("Enter the number of seats in each row:")
    val seats = scanner.nextInt()
    val total = seats * rows
    val nSeats = mutableListOf<Int>()
    val nPurchasedTickets = mutableListOf<Int>()
    val currentIncome = mutableListOf<Int>()
    var prcnt = "0.0"
    for (i in 1..seats) {
        nSeats.add(i - 1, i)
    }
    val cinema = MutableList(rows) {MutableList(seats){ "S" } }
    printMenu(cinema, nSeats, rows, total, scanner, firstHalf, nPurchasedTickets, currentIncome,prcnt, seats,)
}
fun printCinema(cinema :  MutableList<MutableList<String>>, nSeats: MutableList<Int>, rows : Int) {
    println("\nCinema:")
    println("""  ${nSeats.joinToString(" ")}""")
    for (i in 0 until rows) {
        println("""${i + 1} ${cinema[i].joinToString(" ")}""")
    }
}
fun printMenu( cinema :  MutableList<MutableList<String>>, nSeats: MutableList<Int>, rows : Int, total :Int, scanner :Scanner, firstHalf: Int, nPurchasedTickets: MutableList<Int>, currentIncome: MutableList<Int>, prcnt: String, seats : Int) {
    println("\n1. Show the seats \n2. Buy a ticket \n3. Statistics \n0. Exit")
    val choice = readln().toInt()
    mux(choice,cinema, nSeats, rows, total, scanner, firstHalf, nPurchasedTickets, currentIncome,prcnt, seats)
}
fun printStatistics (total: Int, rows: Int, nPurchasedTickets : MutableList<Int>, currentIncome : MutableList<Int>, prcnt : String, seats : Int ) {
    val totalIncome = if (total < 60) 10 * total else 10 * (rows / 2 ) * seats + 8 * (rows - rows / 2) * seats
    var prcnte = prcnt
    try {
        prcnte ="%.2f".format(nPurchasedTickets.sum().toDouble() / total.toDouble() * 100)
    } catch (e: Exception) {
        "0.00"
    }
    println("Number of purchased tickets: ${nPurchasedTickets.sum()} \nPercentage: $prcnte% \nCurrent income: $${currentIncome.sum()} \nTotal income: $$totalIncome")
}
fun userPrompt(scanner: Scanner): Pair<Int, Int> {
    println("\nEnter a row number:")
    val desiredRow = scanner.nextInt()
    println("Enter a seat number in that row:")
    val desiredSeat = scanner.nextInt()
    return Pair(desiredRow, desiredSeat)
}
fun mux (choice: Int, cinema :  MutableList<MutableList<String>>, nSeats: MutableList<Int>, rows : Int, total :Int, scanner :Scanner, firstHalf: Int, nPurchasedTickets: MutableList<Int>, currentIncome: MutableList<Int>, prcnt: String, seats : Int){
    var flag = true
    var flag2 = true
    when(choice) {
        1 -> {
            printCinema(cinema, nSeats, rows)
            printMenu(cinema, nSeats, rows, total, scanner, firstHalf, nPurchasedTickets, currentIncome,prcnt, seats)
        }
        2 -> {
            val (desiredRow, desiredSeat) = userPrompt(scanner)
            if (desiredRow !in 0..rows || desiredSeat !in nSeats) {
                flag = false
                println("Wrong input!")
            } else if (desiredRow in 0..rows && desiredSeat in nSeats && cinema[desiredRow-1][desiredSeat-1] == "B"){
                flag2 = false
                println("That ticket has already been purchased!")
            }
            when(flag && flag2){
                true -> {
                    if ( total < 60 || (total > 60 && desiredRow in 1..firstHalf)) {
                        println("Ticket price: $10")
                        cinema[desiredRow - 1][desiredSeat - 1] = "B"
                        nPurchasedTickets.add(1)
                        currentIncome.add(10)
                    }else {
                        println("Ticket price: $8")
                        cinema[desiredRow - 1][desiredSeat - 1] = "B"
                        nPurchasedTickets.add(1)
                        currentIncome.add(8)
                    }
                }
                false -> mux(2,cinema, nSeats, rows, total, scanner, firstHalf, nPurchasedTickets, currentIncome,prcnt, seats)
            }
            printMenu(cinema, nSeats, rows, total, scanner, firstHalf,nPurchasedTickets, currentIncome,prcnt,seats)
        }
        3 -> {
            printStatistics(total, rows, nPurchasedTickets, currentIncome, prcnt, seats)
            printMenu(cinema, nSeats, rows, total, scanner, firstHalf,nPurchasedTickets, currentIncome,prcnt,seats)
        }
        0 -> return
    }
}
