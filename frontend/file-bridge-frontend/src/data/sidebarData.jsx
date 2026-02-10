import {
  LayoutDashboard,
  Upload,
  Folder,
  CreditCard,
  Receipt
} from "lucide-react";

export const sidebarLinks = [
  {
    id: 1,
    name: "Dashboard",
    icon: LayoutDashboard,
    path: "/dashboard",
  },
  {
    id: 2,
    name: "Uploads",
    icon: Upload,
    path: "/upload",
  },
  {
    id: 3,
    name: "My Files",
    icon: Folder,
    path: "/my-files",
  },
  {
    id: 4,
    name: "Credits",
    icon: CreditCard,
    path: "/subscription",
  },
  {
    id: 5,
    name: "Transactions",
    icon: Receipt,
    path: "/transactions",
  },
];
