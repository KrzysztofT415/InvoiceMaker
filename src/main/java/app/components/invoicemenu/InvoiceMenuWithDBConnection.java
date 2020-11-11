package app.components.invoicemenu;

import app.DataBaseAccess;

import java.util.LinkedList;

public class InvoiceMenuWithDBConnection extends InvoiceMenu {
    private final DataBaseAccess dataBaseAccess;

    private Invoice currentInvoice;
    private final LinkedList<Invoice> invoices;

    InvoiceMenuWithDBConnection(DataBaseAccess dataBaseAccess) {
        this.dataBaseAccess = dataBaseAccess;
        this.invoices = this.getInvoices();
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
        this.dataBaseAccess.addInvoice(this.currentInvoice);
        return currentInvoice.getClientId() + "." + (invoices.indexOf(currentInvoice) + 1);
    }

    @Override
    public void getInvoice(int invoiceId) {
        this.currentInvoice = this.dataBaseAccess.getInvoice(invoiceId);
    }

    @Override
    public LinkedList<Invoice> getInvoices() {
        LinkedList<Invoice> invoicesReload = new LinkedList<>();
        for (Invoice invoice : dataBaseAccess.getInvoices()) {
            invoicesReload.addLast(invoice);
        }
        return invoicesReload;
    }

}
