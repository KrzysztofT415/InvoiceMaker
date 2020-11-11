package app.components.invoicemenu;

import app.DataBaseAccess;

import java.util.LinkedList;

public class InvoiceMenu implements InvoiceManagement {

    private Invoice currentInvoice;
    private LinkedList<Invoice> invoices;

    InvoiceMenu() {
        this.invoices = new LinkedList<>();
    }

    @Override
    public void newInvoice(int clientId) {
        this.currentInvoice = new Invoice(clientId);
    }

    @Override
    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    @Override
    public void addArticle(int articleId, int quantity) {
        this.currentInvoice.addArticle(articleId, quantity);
    }

    @Override
    public void changeArticleQuantity(int articleId, int quantity) {
        this.currentInvoice.changeArticleQuantity(articleId, quantity);
    }

    @Override
    public void removeArticle(int articleId) {
        this.currentInvoice.removeArticle(articleId);
    }

    @Override
    public String saveCurrentInvoice() {
        this.invoices.addLast(this.currentInvoice);
        return currentInvoice.getClientId() + "." + (invoices.indexOf(currentInvoice) + 1);
    }

    @Override
    public void getInvoice(int invoiceId) {
        this.currentInvoice = this.invoices.get(invoiceId);
    }

    @Override
    public LinkedList<Invoice> getInvoices() {
        return invoices;
    }

}
