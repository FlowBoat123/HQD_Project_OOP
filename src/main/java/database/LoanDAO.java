package database;

import javax.sql.DataSource;

public class LoanDAO {

  private DataSource dataSource;

  public LoanDAO(){
    this.dataSource = DataSourceFactory.getDataSource();
  }

  void loanBook(){

  }
}
