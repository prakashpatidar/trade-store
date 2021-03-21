# trade-store
trade-store features:
1. Process and store trades in store.  
2. Manage life cycle of trade.
3. Validate trade and throws exception if maturity date is alredy expired.
4. Throws exception if higher version record is already stored in trade store.
5. Automaticaly expire trade based on maturity date expiry.
6. Overwrite trades if incoming message version is greater or eqaul to stored version in trade store. 
