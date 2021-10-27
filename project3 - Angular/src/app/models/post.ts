import { IImageMap } from "./images";
import { IUser } from "./user";

export interface IPost{
  postId: number;
  postText: string;
  likeNum: number;
  createdTime: Date;
  author: IUser;
  imageList: IImageMap[];
  likes: IUser[];
}