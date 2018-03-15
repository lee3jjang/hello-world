import pandas as pd
import numpy as np

df = pd.read_excel("salesfunnel.xlsx")
df["Status"] = df["Status"].astype("category")
df["Status"].cat.set_categories(["won","pending","presented","declined"], inplace=True)

table = pd.pivot_table(df, index=["Manager","Status"], columns=["Product"], values=["Quantity", "Price"], aggfunc={"Quantity":len, "Price":[np.sum]}, fill_value=0)
filtered = table.query('Manager == ["Debra Henley"]')
filtered.iloc[1][1]
