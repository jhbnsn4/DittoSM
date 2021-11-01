import { IImageMap } from "./imagemap";
import { IUserAccount } from "./useraccount";

export interface IPost {
  postId: number;
  text: string;
  numLikes: number;
  createdTime: Date;
  authorFK: IUserAccount;
  imageList: IImageMap[];
  likes: IUserAccount[];

}

