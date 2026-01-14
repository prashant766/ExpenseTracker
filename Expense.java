package ExpenseTracker;

class Expense {
    private int id;
    private String title;
    private double amount;
    private String date;

    public Expense(int id, String title, double amount, String date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }

    public void display() {
        System.out.println(id + " | " + title + " | " + amount + " | " + date);
    }
}
