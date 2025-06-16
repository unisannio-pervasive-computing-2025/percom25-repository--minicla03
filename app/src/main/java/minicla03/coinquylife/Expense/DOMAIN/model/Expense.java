package minicla03.coinquylife.Expense.DOMAIN.model;

import java.util.Date;
import java.util.List;

public class Expense {

    private String id;

    private String description;

    private Double amount;

    private String createdBy; // User ID of who created the expense

    private Date createdDate;

    private CategoryExpense category;

    private String houseId;

    private List<String> participants; // List of user IDs participating in this expens

    private StatusExpense status;

    public Expense() {

    }

    public Expense(String description, Double amount, String createdBy, Date createdDate, CategoryExpense category, String houseId, List<String> participants)
    {
        this.description = description;
        this.amount = amount;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.category = category;
        this.houseId = houseId;
        this.participants = participants;
        this.status=StatusExpense.PENDING;
    }

    public Expense(String description, Double amount, String createdBy, CategoryExpense category, String houseId, List<String> participants) {
        this.description = description;
        this.amount = amount;
        this.createdBy = createdBy;
        this.category = category;
        this.houseId = houseId;
        this.participants = participants;
        this.status=StatusExpense.PENDING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public CategoryExpense getCategory() {
        return category;
    }

    public void setCategory(CategoryExpense category) {
        this.category = category;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public StatusExpense getStatus() {
        return status;
    }

    public void setStatus(StatusExpense status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", category=" + category +
                ", houseId='" + houseId + '\'' +
                ", participants=" + participants +
                ", status=" + status +
                '}';
    }
}
